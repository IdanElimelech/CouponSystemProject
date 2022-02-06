import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Coupon } from 'src/app/model/coupon.model';
import { CompanyService } from '../../company-service';

@Component({
  selector: 'app-coupon-new',
  templateUrl: './coupon-new.component.html',
  styleUrls: ['./coupon-new.component.css']
})
export class CouponNewComponent implements OnInit {
  @ViewChild("f") addForm: NgForm
  couponId: string
  token: string

  //form values
  @ViewChild("title") fTitle: NgModel
  @ViewChild("start") fStartDate: NgModel
  @ViewChild("end") fEndDate: NgModel
  @ViewChild("cat") fCategory: NgModel
  @ViewChild("am") fAmount: NgModel
  @ViewChild("desc") fDescription: NgModel
  @ViewChild("pr") fPrice: NgModel
  @ViewChild("im") fImage: NgModel

  constructor(private companyService: CompanyService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.parent.params.subscribe((params: Params) => {
      this.token = params["token"]
    })
  }

  submitNewCoupon() {
    let newCoupon = this.setCoupon()
    this.companyService.addCoupon(newCoupon).subscribe(coupon => {
      console.log(coupon.id + " added");
      this.router.navigate(["company/" + this.token])
    })
  }
  
  setCoupon(): Coupon {
    let coupon = new Coupon(this.fTitle.value, this.fStartDate.value, this.fEndDate.value, this.fCategory.value,
      this.fAmount.value, this.fDescription.value, this.fPrice.value, this.fImage.value)
      console.log(coupon);
      
      return coupon
    }
    
    onCancelClicked() {
    this.router.navigate(["company/" + this.token])
  }
}

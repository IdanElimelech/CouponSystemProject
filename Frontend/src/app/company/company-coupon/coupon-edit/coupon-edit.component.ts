import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Coupon } from 'src/app/model/coupon.model';
import { StorageServiceComponent } from 'src/app/storage-service.component';
import { CompanyService } from '../../company-service';

@Component({
  selector: 'app-coupon-edit',
  templateUrl: './coupon-edit.component.html',
  styleUrls: ['./coupon-edit.component.css']
})
export class CouponEditComponent implements OnInit {
  @ViewChild("f") editForm: NgForm
  couponId: string
  token: string
  coupon: Coupon


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
    this.route.params.subscribe((params: Params) => {
      this.couponId = params["id"]
    })
    this.route.parent.params.subscribe((params: Params) => {
      this.token = params["token"]
    })

    this.companyService.getCouponById(this.couponId, this.token).subscribe((coupon) => {
      this.coupon = coupon
    })
  }

  submitEditedCoupon() {
    let patchedCoupon = this.patchCoupon(this.coupon)
    this.companyService.updateCoupon(patchedCoupon).subscribe(coupon => {
      this.router.navigate(["company/" + this.token])
    })
  }

  patchCoupon(coupon: Coupon): Coupon {
    coupon.title = this.fTitle.value
    coupon.startDate = this.fStartDate.value
    coupon.endDate = this.fEndDate.value
    coupon.category = this.fCategory.value
    coupon.amount = this.fAmount.value
    coupon.price = this.fPrice.value
    coupon.description = this.fDescription.value
    coupon.image = this.fImage.value

    return coupon
  }

  onCancelEdit() {
    this.router.navigate(["company/" + this.token])
  }
  // ****Project****
  // Finishes:
  // active classes fix
  // Customer purchase button
}

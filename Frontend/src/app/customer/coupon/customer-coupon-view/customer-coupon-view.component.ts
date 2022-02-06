import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Coupon } from 'src/app/model/coupon.model';
import { StorageServiceComponent } from 'src/app/storage-service.component';
import { CustomerService } from '../../customer/customer-service';

@Component({
  selector: 'app-customer-coupon-view',
  templateUrl: './customer-coupon-view.component.html',
  styleUrls: ['./customer-coupon-view.component.css']
})
export class CustomerCouponViewComponent implements OnInit {
  coupons: Coupon[]
  token: string

  constructor(private route: ActivatedRoute, private customerService: CustomerService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
    })
    //Default
    this.customerService.getAvailableCoupons(this.token).subscribe(coupons => {
      this.coupons = coupons
    })
    this.customerService.onCouponsChanged.subscribe(coupons => {
      this.coupons = coupons
    })
    
  }


}

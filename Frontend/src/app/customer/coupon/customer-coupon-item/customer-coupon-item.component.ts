import { Component, Input, OnInit } from '@angular/core';
import { Coupon } from 'src/app/model/coupon.model';
import { CustomerService } from '../../customer/customer-service';

@Component({
  selector: 'app-customer-coupon-item',
  templateUrl: './customer-coupon-item.component.html',
  styleUrls: ['./customer-coupon-item.component.css']
})
export class CustomerCouponItemComponent implements OnInit {
  @Input() coupon: Coupon
  expandDetails: boolean
  errorOccurred: boolean
  couponPurchased: boolean
  couponAboutToExpire: boolean
  WEEK_MILLIS = 604800000

  errorMessage: string

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.expandDetails = false
    this.couponAboutToExpire = new Date(this.coupon.endDate).getTime() - Date.now() < this.WEEK_MILLIS
  }

  toggleDetails() {
    this.expandDetails ? this.expandDetails = false : this.expandDetails = true
  }

  formatDate(rawDate: string): string {
    let formatedDate = new Date(rawDate)
    return formatedDate.toString().substring(0, 21)
  }

  purchaseCouponClicked() {
    this.customerService.purchaseCoupon(this.coupon.id).subscribe(coupon => {
      this.customerService.getAvailableCoupons(this.customerService.token).subscribe((coupons) => {
        this.customerService.onCouponsChanged.emit(coupons)
      })
    }, (error) => {
      this.errorOccurred = true
      this.errorMessage = error.error.message
    })
  }

  stringifyDate(date: string): string {
    return new Date(date).toString().substring(0, 21)
  }
}

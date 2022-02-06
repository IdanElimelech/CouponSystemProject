import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CustomerService } from '../../customer/customer-service';

@Component({
  selector: 'app-customer-coupon-view-editor',
  templateUrl: './custumer-coupon-view-editor.component.html',
  styleUrls: ['./custumer-coupon-view-editor.component.css']
})
export class CustumerCouponViewEditorComponent implements OnInit {
  selectedFilter: string
  errorMessage: string
  errorOccurred: boolean

  constructor(private customerService: CustomerService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.errorOccurred = false
    this.selectedFilter = "all"
  }

  viewAllAvailableCoupons() {
    this.selectedFilter = "all"
    this.customerService.getAvailableCoupons(this.customerService.token).subscribe(coupons => {
      this.customerService.onCouponsChanged.emit(coupons)
      this.errorOccurred = false
    }, (error) => {
      this.errorOccurred = true
      this.errorMessage = error.error.message
    })
  }

  viewPurchasedCoupons() {
    this.customerService.getCustomerPurchasedCoupons(this.customerService.token).subscribe((coupons) => {
      this.customerService.onCouponsChanged.emit(coupons)
      this.selectedFilter = "owned"
      this.errorOccurred = false
    }, (error) => {
      this.errorOccurred = true
      this.errorMessage = error.error.message
    })

  }
}

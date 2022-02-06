import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgModel } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CompanyService } from '../../company-service';

@Component({
  selector: 'app-company-coupon-view-editor',
  templateUrl: './company-coupon-view-editor.component.html',
  styleUrls: ['./company-coupon-view-editor.component.css']
})
export class CouponViewEditorComponent implements OnInit {
  TYPE_ALL_COUPONS = 0
  TYPE_CATEGORY = 1
  TYPE_END_DATE = 2
  TYPE_PRICE = 3
  filterType: number

  @ViewChild("cat") fCategory: NgModel
  @ViewChild("end") fEndDate: NgModel
  @ViewChild("pr") fPrice: NgModel

  selectedFilter: string

  errorOccurred: boolean
  errorMessage: string

  constructor(private router: Router, private route: ActivatedRoute, private companyService: CompanyService) { }

  ngOnInit(): void {
    this.selectedFilter = "btn0"
    this.errorOccurred = false
  }

  // View all Company Coupons/Default Coupons
  viewMyCouponsClicked() {
    this.filterType = this.TYPE_ALL_COUPONS
    this.selectedFilter = "btn".concat(this.filterType.toString())
  }

  filterByCategoryClicked() {
    this.filterType = this.TYPE_CATEGORY
    this.selectedFilter = "btn".concat(this.filterType.toString())
  }
  filterByEndDateClicked() {
    this.filterType = this.TYPE_END_DATE
    this.selectedFilter = "btn".concat(this.filterType.toString())
  }

  filterByPriceClicked() {
    this.filterType = this.TYPE_PRICE
    this.selectedFilter = "btn".concat(this.filterType.toString())
  }




  filterCoupons() {
    switch (true) {
      case this.filterType == this.TYPE_ALL_COUPONS:
        this.companyService.getCompanyCoupons(this.companyService.token).subscribe(coupons => {
          this.companyService.onCouponsChanged.emit(coupons)
          this.errorOccurred = false
        }, (error) => {
          this.errorMessage = error.error.message
          this.errorOccurred = true
        })
        break
      case this.filterType == this.TYPE_CATEGORY:
        if (this.fCategory.value > 0) {
          this.companyService.getCompanyCouponsByCategory(this.fCategory.value).subscribe(coupons => {
            this.companyService.onCouponsChanged.emit(coupons)
            this.errorOccurred = false
          }, (error) => {
            this.errorMessage = error.error.message
            this.errorOccurred = true
          })
        }
        break
      case this.filterType == this.TYPE_END_DATE:
        if (this.fEndDate.valid) {
          this.companyService.getCompanyCouponsBeforeEndDate(this.fEndDate.value).subscribe(coupons => {
            this.companyService.onCouponsChanged.emit(coupons)
            this.errorOccurred = false
          }, (error) => {
            this.errorMessage = error.error.message
            this.errorOccurred = true
          })
        }
        break
      case this.filterType == this.TYPE_PRICE:
        if (this.fPrice.value > 0) {
          this.companyService.getCompanyCouponsLowerThanPrice(this.fPrice.value).subscribe(coupons => {
            this.companyService.onCouponsChanged.emit(coupons)
            this.errorOccurred = false
          }, (error) => {
            this.errorMessage = error.error.message
            this.errorOccurred = true
          })
        }
        break
    }
  }
}

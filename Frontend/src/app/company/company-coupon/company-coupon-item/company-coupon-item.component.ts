import { HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Coupon } from 'src/app/model/coupon.model';
import { CompanyService } from '../../company-service';
import { CompanyComponent } from '../../company.component';

@Component({
  selector: 'app-company-coupon-item',
  templateUrl: './company-coupon-item.component.html',
  styleUrls: ['./company-coupon-item.component.css']
})
export class CompanyCouponItemComponent implements OnInit {
  @Input() coupon: Coupon
  couponId: number
  deleteRequested: boolean
  expandDetails: boolean
  couponAboutToExpire: boolean
  WEEK_MILLIS = 604800000

  constructor(private router: Router, private route: ActivatedRoute, private companyService: CompanyService) { }

  ngOnInit(): void {
    this.deleteRequested = false
    this.expandDetails = false
    this.couponAboutToExpire = new Date(this.coupon.endDate).getTime() - Date.now() < this.WEEK_MILLIS
  }

  onEditCoupon(coupon: Coupon) {
    this.couponId = coupon.id
    this.router.navigate(["edit/" + this.couponId], { relativeTo: this.route })
  }

  onDeleteCoupon() {
    if (this.deleteRequested) {
      this.companyService.deleteCoupon(this.coupon.id.toString()).subscribe((id) => {
        this.companyService.getCompanyCoupons(this.companyService.token).subscribe((coupons) => {
          this.companyService.onCouponsChanged.emit(coupons)
        })
      })

      this.deleteRequested = false
    } else {
      this.deleteRequested = true
    }
  }

  stringifyDate(date: string): string {
    return new Date(date).toString().substring(0, 21)
  }

  onDetailsClicked() {
    this.expandDetails == false ? this.expandDetails = true : this.expandDetails = false
  }
}

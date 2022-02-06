import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Coupon } from 'src/app/model/coupon.model';
import { CompanyService } from '../../company-service';

@Component({
  selector: 'app-company-coupon-view',
  templateUrl: './company-coupon-view.component.html',
  styleUrls: ['./company-coupon-view.component.css']
})
export class CompanyCouponViewComponent implements OnInit {
  coupons: Coupon[]
  token: string

  sortType: string

  constructor(private route: ActivatedRoute, private companyService: CompanyService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
    })
    this.companyService.getCompanyCoupons(this.token).subscribe(coupons => {
      this.coupons = coupons
    })

    this.companyService.onCouponsChanged.subscribe(coupons => {
      this.coupons = coupons
    })

  }


  printCoupons(): string {
    return this.coupons.toString()
  }

  sortCouponsByTitle() {
    if (this.coupons != null) {
      this.sortType = "title"
      this.coupons.sort((c1, c2) => c1.title.localeCompare(c2.title))
    }
  }

  sortCouponsBySalesAmount() {
    if (this.coupons != null) {
      this.sortType = "sales"
      this.companyService.getCompanyCouponsSortedBySalesAmount().subscribe((coupons) => {
        this.coupons = coupons
      })
    }
  }

  sortCouponsByStartDate() {
    if (this.coupons != null) {
      this.sortType = "start"
      this.coupons.sort((c1, c2) => {
        let c1date = new Date(c1.startDate)
        let c2date = new Date(c2.startDate)
        if (c1date > c2date) {
          return -1
        } else if (c1date == c2date) {
          return 0
        } else {
          return 1
        }
      })
    }
  }

  sortCouponsByEndDate() {
    if (this.coupons != null) {
      this.sortType = "end"
      this.coupons.sort((c1, c2) => {
        let c1date = new Date(c1.endDate)
        let c2date = new Date(c2.endDate)
        if (c1date > c2date) {
          return -1
        } else if (c1date == c2date) {
          return 0
        } else {
          return 1
        }
      })
    }
  }
}

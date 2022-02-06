import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Company } from 'src/app/model/company.model';
import { CompanyService } from '../company-service';

@Component({
  selector: 'app-company-header',
  templateUrl: './company-header.component.html',
  styleUrls: ['./company-header.component.css']
})
export class CompanyHeaderComponent implements OnInit {
  token: string
  company: Company

  selectedItem: string

  constructor(private companyService: CompanyService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
    })
    

    this.companyService.fetchCompany(this.token).subscribe((company) => {
      this.company = company
    })

    this.selectedItem = "view"
  }

  addNewCouponClicked() {
    this.router.navigate(["new"], { relativeTo: this.route })
    this.selectedItem = "add"
  }

  editProfileClicked() {
    this.router.navigate(["edit-profile"], { relativeTo: this.route })
    this.selectedItem = "edit"
  }

  viewCouponsClicked() {
    this.router.navigate(["company/" + this.token])
    this.selectedItem = "view"
  }
  
  logoutClicked() {
    this.companyService.logout().subscribe(id=>{
      this.router.navigate(["login"])
    })
  }
}

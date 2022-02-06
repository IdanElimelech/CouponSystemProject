import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Company } from 'src/app/model/company.model';
import { CompanyService } from '../company-service';

@Component({
  selector: 'app-edit-company-profile',
  templateUrl: './edit-company-profile.component.html',
  styleUrls: ['./edit-company-profile.component.css']
})
export class EditCompanyProfileComponent implements OnInit {
  company: Company

  constructor(private router: Router, private route: ActivatedRoute, private companyService: CompanyService) { }

  @ViewChild("name") fName: NgModel
  @ViewChild("email") fEmail: NgModel
  @ViewChild("pass") fPassword: NgModel
  @ViewChild("f") editForm: NgForm

  ngOnInit(): void {
    this.companyService.fetchCompany(this.companyService.token).subscribe(company =>
      this.company = company)
  }

  submitEditedCompany() {
    if (this.editForm.valid) {
      this.company.name = this.fName.value
      this.company.email = this.fEmail.value
      this.company.password = this.fPassword.value
      this.companyService.updateCompany(this.company).subscribe(company => {
        this.router.navigate(["company/" + this.companyService.token])
      })
    }
  }

  onCancelEdit() {
    this.router.navigate(["company/" + this.companyService.token])
  }
}

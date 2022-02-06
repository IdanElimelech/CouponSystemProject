import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Company } from '../model/company.model';
import { StorageServiceComponent } from '../storage-service.component';
import { CompanyService } from './company-service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css'],
  providers: [CompanyService]
})
export class CompanyComponent implements OnInit {
  company: Company
  token: string

  constructor(private route: ActivatedRoute, private companyService: CompanyService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
      this.companyService.fetchCompany(this.token).subscribe((company) => {
        this.company = company
      })

    })
  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Customer } from 'src/app/model/customer.model';
import { StorageServiceComponent } from 'src/app/storage-service.component';
import { CustomerService } from './customer-service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css'],
  providers: [CustomerService]
})
export class CustomerComponent implements OnInit {
  private token: string
  private customer: Customer

  constructor(private route: ActivatedRoute, private customerService: CustomerService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
      this.customerService.fetchCustomer(this.token).subscribe(customer => {
        this.customer = customer
      })
    })
  }

}

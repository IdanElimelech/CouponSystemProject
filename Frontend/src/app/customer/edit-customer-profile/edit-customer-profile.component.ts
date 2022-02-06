import { Component, OnInit, ViewChild } from '@angular/core';
import { NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { Customer } from 'src/app/model/customer.model';
import { CustomerService } from '../customer/customer-service';

@Component({
  selector: 'app-edit-customer-profile',
  templateUrl: './edit-customer-profile.component.html',
  styleUrls: ['./edit-customer-profile.component.css']
})
export class EditCustomerProfileComponent implements OnInit {
  customer: Customer

  @ViewChild("fName") fFirstName: NgModel
  @ViewChild("lName") fLastName: NgModel
  @ViewChild("email") fEmail: NgModel
  @ViewChild("pass") fPassword: NgModel

  constructor(private customerService: CustomerService, private router: Router) { }

  ngOnInit(): void {
    this.customerService.fetchCustomer(this.customerService.token).subscribe(customer => {
      this.customer = customer
    })
  }

  onCancelEdit() {
    this.router.navigate(["customer/" + this.customerService.token])
  }

  submitEditedCustomer() {
    this.customer.firstName = this.fFirstName.value
    this.customer.lastName = this.fLastName.value
    this.customer.email = this.fEmail.value
    this.customer.password = this.fPassword.value
    this.customerService.updateCustomer(this.customer).subscribe(patchedCustomer => {
      this.customer = patchedCustomer
      this.router.navigate(["customer/" + this.customerService.token])
    })
  }

}

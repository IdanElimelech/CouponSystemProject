import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Customer } from 'src/app/model/customer.model';
import { CustomerService } from '../customer/customer-service';

@Component({
  selector: 'app-customer-header',
  templateUrl: './customer-header.component.html',
  styleUrls: ['./customer-header.component.css']
})
export class CustomerHeaderComponent implements OnInit {
  customer: Customer
  private token: string

  selectedItem: string

  constructor(private route: ActivatedRoute, private customerService: CustomerService, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
    })

    this.customerService.fetchCustomer(this.token).subscribe((customer) => {
      this.customer = customer
    })
    this.selectedItem = "coupons"
  }

  onEditProfileClicked() {
    this.router.navigate(["edit-profile"], { relativeTo: this.route })
    this.selectedItem = "profile"
  }

  viewCouponsClicked() {
    this.selectedItem = "coupons"
    this.router.navigate(["customer/" + this.token])
  }
  
  logoutClicked() {
    this.customerService.logout().subscribe(id=>{
      this.router.navigate(["login"])
    })
  }

}

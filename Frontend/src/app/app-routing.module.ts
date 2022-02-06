import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin/admin/admin.component';
import { CompanyCouponViewComponent } from './company/company-coupon/company-coupon-view/company-coupon-view.component';
import { CompanyComponent } from './company/company.component';
import { CouponEditComponent } from './company/company-coupon/coupon-edit/coupon-edit.component';
import { CustomerComponent } from './customer/customer/customer.component';
import { LoginComponent } from './login/login.component';
import { CouponViewEditorComponent } from './company/company-coupon/company-coupon-view-editor/company-coupon-view-editor.component';
import { CouponNewComponent } from './company/company-coupon/coupon-new/coupon-new.component';
import { EditCompanyProfileComponent } from './company/edit-profile/edit-company-profile.component';
import { EditCustomerProfileComponent } from './customer/edit-customer-profile/edit-customer-profile.component';
import { CustomerCouponViewComponent } from './customer/coupon/customer-coupon-view/customer-coupon-view.component';

const routes: Routes = [
  { path: "", redirectTo: "/login", pathMatch: "full" },

  { path: "login", component: LoginComponent },
  {
    path: "customer/:token", component: CustomerComponent, children: [
      { path: "", component: CustomerCouponViewComponent },
      { path: "edit-profile", component: EditCustomerProfileComponent }
    ]
  },

  {
    path: "company/:token", component: CompanyComponent, children: [
      { path: "", component: CompanyCouponViewComponent },
      { path: "edit-profile", component: EditCompanyProfileComponent },
      { path: "edit/:id", component: CouponEditComponent },
      { path: "new", component: CouponNewComponent },
    ]
  },

  { path: "admin/:token", component: AdminComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

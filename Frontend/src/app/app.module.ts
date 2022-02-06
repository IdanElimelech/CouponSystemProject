import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CompanyComponent } from './company/company.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CompanyHeaderComponent } from './company/company-header/company-header.component';
import { StorageServiceComponent } from './storage-service.component';
import { CustomerComponent } from './customer/customer/customer.component';
import { AdminComponent } from './admin/admin/admin.component';
import { CompanyCouponViewComponent } from './company/company-coupon/company-coupon-view/company-coupon-view.component';
import { CompanyCouponItemComponent } from './company/company-coupon/company-coupon-item/company-coupon-item.component';
import { CouponViewEditorComponent } from './company/company-coupon/company-coupon-view-editor/company-coupon-view-editor.component';
import { CustomerCouponViewComponent } from './customer/coupon/customer-coupon-view/customer-coupon-view.component';
import { CustumerCouponViewEditorComponent } from './customer/coupon/custumer-coupon-view-editor/custumer-coupon-view-editor.component';
import { CustomerHeaderComponent } from './customer/customer-header/customer-header.component';
import { CustomerCouponItemComponent } from './customer/coupon/customer-coupon-item/customer-coupon-item.component';
import { LoginHeaderComponent } from './login/login-header/login-header.component';
import { CouponEditComponent } from './company/company-coupon/coupon-edit/coupon-edit.component';
import { CouponNewComponent } from './company/company-coupon/coupon-new/coupon-new.component';
import { EditCompanyProfileComponent } from './company/edit-profile/edit-company-profile.component';
import { EditCustomerProfileComponent } from './customer/edit-customer-profile/edit-customer-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    CompanyComponent,
    LoginComponent,
    CompanyHeaderComponent,
    CompanyCouponViewComponent,
    CompanyCouponItemComponent,
    CouponViewEditorComponent,
    CustomerComponent,
    AdminComponent,
    CustomerCouponViewComponent,
    CustumerCouponViewEditorComponent,
    CustomerHeaderComponent,
    CustomerCouponItemComponent,
    LoginHeaderComponent,
    CouponEditComponent,
    CouponNewComponent,
    EditCompanyProfileComponent,
    EditCustomerProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

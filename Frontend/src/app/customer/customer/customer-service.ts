import { HttpClient, HttpParams } from "@angular/common/http"
import { EventEmitter, Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { Coupon } from "src/app/model/coupon.model"
import { Customer } from "src/app/model/customer.model"

@Injectable()
export class CustomerService {
    token: string
    onCouponsChanged = new EventEmitter<Coupon[]>()

    constructor(private http: HttpClient) { }

    fetchCustomer(token: string): Observable<Customer> {
        this.token = token
        return this.http.get<Customer>("http://localhost:8080/api/customer/?token=" + token)
    }


    getCustomerPurchasedCoupons(token: string): Observable<Coupon[]> {
        let params = new HttpParams().set("token", token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/customers/coupons/purchased?" + params)
    }

    getAvailableCoupons(token: string): Observable<Coupon[]> {
        let params = new HttpParams().set("token", token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/customers/coupons?" + params)
    }


    updateCustomer(customer: Customer) {
        let params = new HttpParams()
            .set("firstName", customer.firstName)
            .set("lastName", customer.lastName)
            .set("email", customer.email)
            .set("password", customer.password)
            .set("token", this.token)
        console.log();
        return this.http.post<Customer>("http://localhost:8080/api/customer/edit-profile?", params)
    }

    purchaseCoupon(couponId: number): Observable<Coupon> {
        let params = new HttpParams()
            .set("couponId", couponId.toString())
            .set("token", this.token)
     return this.http.post<Coupon>("http://localhost:8080/api/customer/purchase?", params)
    }
    
    logout():Observable<number> {
        let params = new HttpParams()
            .set("token", this.token)
        return this.http.delete<number>("http://localhost:8080/api/customer/logout?" + params)
    }
}
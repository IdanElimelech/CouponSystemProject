import { HttpClient, HttpParams } from "@angular/common/http";
import { EventEmitter, Injectable, Output } from "@angular/core";
import { Observable } from "rxjs";
import { Company } from "../model/company.model";
import { Coupon } from "../model/coupon.model";

@Injectable()
export class CompanyService {
    token: string
    onCouponsChanged = new EventEmitter<Coupon[]>()
    constructor(private http: HttpClient) { }
    
    /* fetch relevant entity with given token */
    fetchCompany(token: string): Observable<Company> {
        this.token = token
        return this.http.get<Company>("http://localhost:8080/api/company/?token=" + token)
    }
    
        logout():Observable<number> {
            let params = new HttpParams()
                .set("token", this.token)
            return this.http.delete<number>("http://localhost:8080/api/companies/logout?" + params)
        }
    
    /* Get Coupons */
    // Company - getCompanyCoupons (default)
    getCompanyCoupons(token: string): Observable<Coupon[]> {
        return this.http.get<Coupon[]>("http://localhost:8080/api/companies/coupons/?token=" + token)
    }

    getCompanyCouponsByCategory(category: string): Observable<Coupon[]> {
        let params = new HttpParams()
            .set("category", category)
            .set("token", this.token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/companies/coupons/by-category?" + params)
    }
    getCompanyCouponsLowerThanPrice(price: string): Observable<Coupon[]> {
        let params = new HttpParams()
            .set("price", price)
            .set("token", this.token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/companies/coupons/by-price?" + params)
    }
    getCompanyCouponsBeforeEndDate(date: string): Observable<Coupon[]> {
        let params = new HttpParams()
            .set("endDate", date)
            .set("token", this.token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/companies/coupons/before?" + params)
    }

    getCompanyCouponsSortedBySalesAmount(): Observable<Coupon[]> {
        let params = new HttpParams()
            .set("token", this.token)
        return this.http.get<Coupon[]>("http://localhost:8080/api/companies/coupons-by-sales?" + params)
    }


    /* Coupon */
    getCouponById(id: string, token: string): Observable<Coupon> {
        let params = new HttpParams()
            .set("id", id)
            .set("token", token)
        return this.http.get<Coupon>("http://localhost:8080/api/companies/coupon/?" + params)
    }

    deleteCoupon(id: string): Observable<string> {
        let params = new HttpParams()
            .set("token", this.token)
            .set("id", id)
        return this.http.delete<string>("http://localhost:8080/api/companies/coupon/del?" + params)
    }

    addCoupon(coupon: Coupon): Observable<Coupon> {
        let params = new HttpParams()
            .set("title", coupon.title)
            .set("startDate", coupon.startDate)
            .set("endDate", coupon.endDate)
            .set("category", coupon.category.toString())
            .set("amount", coupon.amount.toString())
            .set("description", coupon.description)
            .set("price", coupon.price.toString())
            .set("image", coupon.image)
            .set("token", this.token)
        return this.http.post<Coupon>("http://localhost:8080/api/companies/coupons/new?", params)
    }

    updateCoupon(coupon: Coupon): Observable<Coupon> {
        let params = new HttpParams()
            .set("id", coupon.id.toString())
            .set("title", coupon.title)
            .set("startDate", coupon.startDate)
            .set("endDate", coupon.endDate)
            .set("category", coupon.category.toString())
            .set("amount", coupon.amount.toString())
            .set("description", coupon.description)
            .set("price", coupon.price.toString())
            .set("image", coupon.image)
            .set("token", this.token)
        return this.http.post<Coupon>("http://localhost:8080/api/companies/coupons/update?", params)
    }

    updateCompany(company: Company): Observable<Company> {
        let params = new HttpParams()
            .set("name", company.name)
            .set("email", company.email)
            .set("password", company.password)
            .set("token", this.token)
        return this.http.post<Company>("http://localhost:8080/api/companies/edit-profile?", params)
    }

}
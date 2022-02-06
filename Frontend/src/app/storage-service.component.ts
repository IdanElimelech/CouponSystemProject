import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Admin } from './model/admin.model';
import { Company } from './model/company.model';
import { Coupon } from './model/coupon.model';
import { Customer } from './model/customer.model';
import { Token } from './token';

@Injectable()
export class StorageServiceComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  fetchAdmin(token: string): Observable<Admin> {
    return this.http.get<Admin>("http://localhost:8080/api/admin/?token=" + token)
  }
  /**/


  /* Company Service */

}

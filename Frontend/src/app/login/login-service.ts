import { HttpClient, HttpParams } from "@angular/common/http"
import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { Token } from "../token"

@Injectable()
export class LoginService {
    constructor(private http:HttpClient){}


     /* Login Request */
  login(email: string, password: string, logType: number): Observable<Token> {
    let params = new HttpParams()
      .set("email", email)
      .set("password", password)
      .set("logType", String(logType))

    return this.http.post<Token>("http://localhost:8080/api/login", params)
  }

}
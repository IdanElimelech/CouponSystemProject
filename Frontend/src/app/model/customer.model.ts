import { Coupon } from "./coupon.model"

export class Customer {
    id: number
    firstName: string
    lastName: string
    email: string
    password: string

    coupons: Coupon[]

    constructor(firstName: string, lastName: string, email: string, password: string) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}
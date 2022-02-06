import { Company } from "./company.model";
import { Customer } from "./customer.model";

export class Coupon {
    id: number
    company: Company
    title: string
    startDate: string
    endDate: string
    category: number
    amount: number
    description: string
    price: number
    image: string
    customers: Customer[]

    constructor(title: string, startDate: string, endDate: string, category: number, amount: number, desc: string, price: number, image: string) {
        this.title = title
        this.startDate = startDate
        this.endDate = endDate
        this.category = category
        this.amount = amount
        this.description = desc
        this.price = price
        this.image = image
    }

    public getCompany(): Company {
        return this.company
    }

    public getId(): number {
        return this.id
    }

    public getTitle(): string {
        return this.title
    }

    public getStartDate(): string {
        return this.startDate
    }

    public getEndDate(): string {
        return this.endDate
    }

    public getCategory(): number {
        return this.category
    }

    public getAmount(): number {
        return this.amount
    }

    public getDescription(): string {
        return this.description
    }

    public getPrice(): number {
        return this.price
    }

    public getCustomers(): Customer[] {
        return this.customers
    }

    public getImage(): string {
        return this.image
    }

    public toString(): string {
        return this.title
    }
}
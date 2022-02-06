export class Company {
    id: number
    name: string
    email: string
    password: string

    constructor(name: string, email: string, password: string) {
        this.name = name
        this.email = email
        this.password = password
    }

    getName(): string {
        return this.name
    }
}
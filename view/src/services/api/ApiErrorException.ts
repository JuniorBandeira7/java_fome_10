export class ApiErrorException extends Error {
    public readonly message: string = ''

    constructor(message: string) {
        super()

        this.message = message
    }
}
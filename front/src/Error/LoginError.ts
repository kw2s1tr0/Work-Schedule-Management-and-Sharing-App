export class ExpectedError extends Error {
    constructor(public status: number, public messages: string[]) {
        super(messages.join(", "));
        this.name = "ExpectedError";
    }
}
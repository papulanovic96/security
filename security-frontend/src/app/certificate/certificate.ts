export class Certificate {
    constructor(
        private id: number,
        private serial_number: number,
        private name: string,
        private status: boolean
    ) {}
}
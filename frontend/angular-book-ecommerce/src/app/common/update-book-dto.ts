export class UpdateBookDTO {
    title: string;
    unitPrice: number;
    description: string;
    // category_id: number;

    constructor(data: any) {
        this.title = data.title;
        this.unitPrice = data.unitPrice;
        this.description = data.description;
        // this.category_id = data.category_id;
    }
}
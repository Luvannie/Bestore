export class Book {
    constructor(public title: string,
                public author: string,
                public publisher: string,
                public publishedDate: Date,
                public description: string,
                public thumbnail: string,
                public language: string,
                public active: boolean,
                public unitsInStock: number,
                public unitPrice: number,
                public dateCreated: Date,
                public lastUpdated: Date
            ) {
    }
}

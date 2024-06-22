import { IsNotEmpty, IsString } from "class-validator";

export class CategoryDTO {    
    
    categoryName: string;
            
    constructor(data: any) {
        this.categoryName = data.categoryName;    
    }
}
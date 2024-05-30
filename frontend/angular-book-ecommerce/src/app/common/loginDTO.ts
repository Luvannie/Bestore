import{IsString, IsNotEmpty} from 'class-validator';

export class LoginDTO {
    @IsString()
    @IsNotEmpty()
    account: string ;

    @IsString()
    @IsNotEmpty()
    password: string;

    role_id: number;

    constructor(data: any){
        this.account = data.account ;
        this.password = data.password ;
        this.role_id = data.role_id ;
    }
}

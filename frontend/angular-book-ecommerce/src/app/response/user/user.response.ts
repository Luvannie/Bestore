import { Role } from "../../common/model/role";

export interface UserResponse {
    id: number;
    username: string;
    phone_number: string;
    is_active: boolean;
    facebook_account_id: number;
    google_account_id: number;
    role: Role;    
}
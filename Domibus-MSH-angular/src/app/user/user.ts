export enum UserState{
  PERSISTED,
  NEW,
  UPDATED
}
export class UserResponseRO {
  userName: string;
  email: string;
  password:string;
  active:boolean;
  authorities: Array<string>;
  roles:string="";
  status:string;



  constructor(userName: string, email: string, password: string, active: boolean, status:string,authorities: Array<string>) {
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.status= status;
    this.active = active;
    this.authorities=authorities;
    for(let authority in authorities){
      this.roles=this.roles.concat(authorities[authority]).concat(" ");
    }
  }
}

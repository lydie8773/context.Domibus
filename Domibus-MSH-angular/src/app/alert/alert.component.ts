import {Component} from "@angular/core";
import {AlertService} from "./alert.service";

@Component({
  moduleId: module.id,
  selector: 'alert',
  templateUrl: 'alert.component.html',
  styleUrls: ['./alert.component.css']
})

export class AlertComponent {
  message: any;

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.getMessage().subscribe(message => { this.message = message; });
  }

  clearAlert():void {
    this.alertService.clearAlert();
  }
}

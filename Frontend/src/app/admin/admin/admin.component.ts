import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Admin } from 'src/app/model/admin.model';
import { StorageServiceComponent } from 'src/app/storage-service.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  private admin: Admin
  private token: string

  constructor(private route: ActivatedRoute, private storage: StorageServiceComponent) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.token = params["token"]
      this.storage.fetchAdmin(this.token).subscribe(admin => {
        this.admin = admin
        // add debug, delete when ready
        console.log(this.admin);
      })
    })
  }

}

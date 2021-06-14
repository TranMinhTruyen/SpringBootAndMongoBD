import { HttpClient } from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 

declare var $: any;
@Component({
  selector: 'app-order-detail',
  templateUrl: './orderdetail.component.html',
  styleUrls: ['./orderdetail.component.css'],
})
export class OrderDetailComponent {
    constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute) {
    }


    ngOnInit() { 
        this.route.queryParams.subscribe( 
        params => { 
            this.id =  params['orderID']; 
            this.totalPrice =  params['totalPrice'];
            console.log(this.id)
            console.log(this.totalPrice)
        }); 
        this.GetOrderDetail();
    } 
    //#region "Khai báo các biến"
    id: Number;
    totalPrice: Number;
    orderDetail: any ={
        data: []
    }
    //#endregion "Khai báo các biến"

    //#region "Lấy chi tiết đơn hàng"
    GetOrderDetail() {
        this.http.get<any>('https://localhost:44343/api/Order/Get_OrderDetail/' + this.id).subscribe(
        result => {
                    var res: any = result;
                    if (res != null) {
                        this.orderDetail = res;
                    }
                    else {
                        alert(res.message);
                    } 
                    },
        error =>  {
                    alert("Server error !");
                    }
        );
    }
    //#endregion "Lấy chi tiết đơn hàng"
}
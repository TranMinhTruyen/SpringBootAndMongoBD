import { HttpClient} from '@angular/common/http';
import { Component, Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router'; 
import { CartComponent } from '../cart/cart.component';
import { ProductService } from './product.service';

declare var $: any;
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent {
    constructor(private http: HttpClient, private productService:ProductService , @Inject('BASE_URL') baseUrl: string, private route: ActivatedRoute) {
    }


    async ngOnInit() { 
        this.route.queryParams.subscribe( 
        params => { 
            this.id = params['productID']; 
        });
        await this.SearchProduct(); 
    } 


    //#region "Khai báo các biến"
    id: String = "";
    nowDate = new Date();
    size: number = 1;
    page: number = 1;
    products: any = {
        data: [],
        totalRecord: 0,
        page: 0,
        size: this.size,
        totalPage: 0,
    }
    cart: any = {
        account: "",
        productID: "",
        amounts: 1,
        note: ""
    }
    amount: any = {
        productID: "",
        unitsInStock: 0,
    }
    //#endregion "Khai báo các biến"


    //#region "Tìm sản phẩm"
    async SearchProduct() {
        this.products = this.productService.getByKeyword(this.size, this.page, this.id)
            .subscribe(response => console.log(response))
    }
    //#endregion "Tìm sản phẩm"


    //#region "Thêm vào giỏ hàng"
    addProductToCart(productID: String, unitsInStock: number)
    {
        try {
            this.cart.account = sessionStorage.getItem('customer');
        }
        catch{
            this.cart.account = null;
        }
        if (this.cart.account == null){
            alert("Bạn phải đăng nhập");
        } else{
            if (unitsInStock == 0){
                alert("Sản phẩm hết hàng")
            }
            else{
                this.cart.account = sessionStorage.getItem('customer');
                this.cart.productID = productID;
                this.http.post('https://localhost:44343/api/Cart/Create_Cart', this.cart).subscribe(
                    result => {
                        var res: any = result;
                        if (res != null){
                            alert("Thêm vào giỏ hàng thành công");
                            this.UpdateAmount(productID,unitsInStock - 1);
                            window.location.reload();
                        }
                        else{
                            alert("Lỗi dữ liệu");
                        }
                },
                    error =>  {
                        alert("Bạn đã thêm sản phẩm này vào giỏ hàng rồi");
                    }
                )
            }
        }
    }
    //#endregion "Thêm vào giỏ hàng"


    //#region "Cập nhật số lượng khi thêm vào giỏ hàng"
    UpdateAmount(productID: String, unitsInStock: number){
        this.amount.productID = productID;
        this.amount.unitsInStock = unitsInStock;
        this.http.put('https://localhost:44343/api/Product/Update_Unit_In_Stock/' + productID, this.amount).subscribe(
        result => {
            var res: any = result;
        },
        error =>  {
            alert("Error");
        }
        )
    }
    //#endregion "Cập nhật số lượng khi thêm vào giỏ hàng"

    setDefaultPic(event) {
        event.target.src = 'assets/placeholder.png';
    }
}



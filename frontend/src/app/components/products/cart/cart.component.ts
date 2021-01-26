import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/models/address.model';
import { Cart } from 'src/app/models/cart.model';
import { ProductInOrder } from 'src/app/models/product-in-order.model';
import { Product } from 'src/app/models/product.model';
import { AuthService } from 'src/app/services/auth.service';
import { CartService } from 'src/app/services/cart.service';
import { GeolocationService } from 'src/app/services/geolocation.service';
import { ManageUserService } from 'src/app/services/manage-user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  orderTotal = 0;
  tax: number = 0;
  total: number = 0;
  shipping: number = 0;
  address: Address = new Address();
  totalAmount: number;
  cart: Cart = new Cart();
  products = this.cart.products;
  userDetails;
  totalBeforeDiscount = 0;
  error;
  deliveryCharge;

  constructor(
    public cartService: CartService,
    private authService: AuthService,
    private manageUserService: ManageUserService,
    private geolocationService: GeolocationService
  ) {}

  ngOnInit(): void {
    this.fetchCart();
  }

  fetchCart() {
    this.cartService.fetchCartByUserId().subscribe((data: Cart) => {
      console.log(data);

      this.manageUserService
        .fetchById(this.authService.fetchFromSessionStorage()?.userId)
        .subscribe((user) => {
          this.address = user['address'];
          this.cart.cartId = data.cartId;
          this.cart.products = data.products;
          this.products = this.cart.products;
          this.calculateDistance();
          this.calculateOrderSummary(this.products);
        });
    });
  }

  removeItem(productId: number) {
    console.log('Inside Remove');
    this.cartService.removeItemFromCart(productId).subscribe((data) => {
      this.fetchCart();
    });
  }

  calculateOrderSummary(productsInOrder: ProductInOrder[]) {
    console.log(productsInOrder);

    let discountPrice = 0;
    let totalBeforeDiscount = 0;
    let total;
    for (const key in productsInOrder) {
      console.log(productsInOrder[key]);
      totalBeforeDiscount += productsInOrder[key].productPrice;
      if (productsInOrder[key].discountPercent === 0)
        discountPrice = productsInOrder[key].productPrice;
      else discountPrice += this.calculatePrice(productsInOrder[key]);
    }
    this.totalBeforeDiscount = totalBeforeDiscount;

    this.orderTotal = discountPrice;

    if (this.orderTotal < 2000 && this.orderTotal > 0) {
      this.shipping = 40;
    } else {
      this.shipping = 0;
    }

    total = this.orderTotal + this.shipping + this.deliveryCharge;
    this.total = total;
  }

  checkout() {
    sessionStorage.setItem('shippingAddress', this.address.area);
    sessionStorage.setItem('City', this.address.city);
    sessionStorage.setItem('State', this.address.state);
    sessionStorage.setItem('Pincode', this.address.pincode);
  }

  calculateDistance() {
    this.geolocationService.fetchDistance(this.address.pincode).subscribe(
      (res) => {
        if (res['info'].statuscode !== 0)
          this.error = 'error';
        else {
          this.error = null;
          console.log(res['route'].distance);
          this.calculateDeliveryCharge(res['route'].distance);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

  calculateDeliveryCharge(distance) {
    if (distance <= 5) this.deliveryCharge = 50;
    else if (distance <= 50) this.deliveryCharge = 200;
    else this.deliveryCharge = 500;
  }
  calculatePrice(currentProduct: ProductInOrder) {
    return (
      currentProduct.productPrice -
      currentProduct.productPrice * 0.01 * currentProduct.discountPercent
    );
  }
}

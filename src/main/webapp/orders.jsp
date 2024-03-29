<%@ page import="services.CriterionService"%>
<%@ page import="models.enums.*"%>
<%@ page import="java.util.List"%>
<%@ page import="services.entity.*"%>
<%@ page import="intarfaces.Entity"%>
<%@ page import="models.*"%>
<%@ page import="services.servlets.StoreServlet"%>
<%@ page import="services.ServiceHibernate"%>
<%@ page import="services.CartService"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
    /*START SETTINGS*/
    *, *::before, *::after{
        box-sizing: border-box;
    }

    body{
        background-color: #e8ffe1;
    }


    /*HEADER*/
    .nav-box{
        position: relative;
        margin: auto;
        width: 95%;
        border-radius: 10px;
        background-color: #009800;
    }

    .nav-container{
        display: flex;
        align-items: center;
        width: 100%;
        height: 80px;
    }

    input, input::placeholder{
        font: 20px/3 Helvetica;
    }

    .search-bar{
        display: flex;
        align-items: center;
        height: 45%;
        width: 22%;
        margin-left: 3%;
        border-radius: 5px;
        border: 0;
    }

    .search{
        height: 100%;
        width: 100%;
        margin-right: 17px;
        border-radius: 5px;
        border: 0;
    }

    .search-button img{
        position: relative;
        top: 2px;
        width: 35px;
        border-radius: 50%;
        border: 0;
    }

    .button-bar{
        display: flex;
        align-items: center;
        height: 100%;
        margin-left: auto;
        margin-right: 3%;
    }

            .button-bar div{
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                text-align: center;
                height: 60%;
                margin-right: 40px;
                margin-left: 5px;
                background-color: #009800;
                color: white;
                font-size: 22px;
                font-family: Helvetica;
                border: 0;
            }

            .button-bar div a{

                width: 100%;
                color: white;
                text-decoration: none;
                border: none;
            }

            .button-bar div:nth-of-type(4){
                margin-left: 20px;
                font-weight: bold;
            }

    .button-bar div::after{
        display: block;
        content: '';
        height: 2px;
        width: 0%;
        background-color: white;
        transition: 0.5s;
    }

    .button-bar div:hover::after{
        display: block;
        content: '';
        height: 2px;
        width: 100%;
        background-color: white;
        transition: 0.5s;
    }


    /*CONTENT*/
    .content-box{
        width: 80%;
        margin-top: 30px;
        margin-left: 10%;
    }

    .chapter{
        font-family: 'Trebuchet MS';
        margin-top: 50px;
        margin-bottom: 20px;
    }

    .chapter::after{
        display: block;
        content: '';
        height: 3px;
        width: 100%;
        background-color: black;
        transition: 0.5s;
    }

    .store{
        display: flex;
        justify-content: space-evenly;
        flex-wrap: wrap;
    }

    .cart{
        display: flex;
        justify-content: space-evenly;
        flex-direction: column;
        flex-wrap: wrap;
    }

    .game-name{
        margin: 0;
        text-align: center;
    }

    .store-game, .cart-game, .cart-game-complete{
        color: black;
        text-decoration: none;
        border: none;
    }


    /*STORE*/
    .store-game{
        width: 230px;
        margin-top: 30px;
        margin-left: 30px;
        margin-right: 30px;
        padding-bottom:50px;
        border-radius: 5px;
    }

    .store-game:hover{
        background-color: #B8EAA8;
    }

    .store-game h2{
        font-family: 'Trebuchet MS';
    }

    .store-game img{
        height: 329px;
        width: 230px;
        border-radius: 5px;
    }


    /*CART*/
    .cart-game{
        display: flex;
        align-items: center;
        width: 100%;
        margin: 30px;
        margin-left: 0;
        margin-bottom: 10px;
        border-radius: 5px;
    }


    .cart-game img{
        height: 100px;
        border-radius: 5px;
    }

    .cart-game:hover{
        background-color: #B8EAA8;
    }

    .cart-game h2{
        font-family: 'Trebuchet MS';
        margin-left: 15px;
    }

    .cart-game-complete{
        display: flex;
        align-items: center;
        width: 100%;
        margin: 30px;
        margin-left: 0;
        margin-bottom: 10px;
        border-radius: 5px;
        color: #009800;
    }

    .cart-game-complete img{
        height: 100px;
        border-radius: 5px;
    }

     .cart-game-complete:hover{
        background-color: #B8EAA8;
     }
    .cart-game-complete h2{
        font-family: 'Trebuchet MS';
        margin-left: 15px;
    }

    /*LOG IN*/
    .login{
        height: 450px;
        width: 100%;
        background-image:
        linear-gradient(to left, rgba(232, 255, 225, 0), rgba(232, 255, 225, 1)),
         url('/Обучение/Универ/2 КУРС/2 семестр/ТКП/Kursovaja/GameStore/src/main/java/web/img/log_in.jpg');
         background-size: cover;
    }

    .name{
        font-family: 'Helvetica';
        font-size: 30px;
        text-align: center;
    }

    .name::after{
        display: block;
        content: '';
        height: 2px;
        width: 100%;
        background-color: black;
        margin-top: 15px;
        margin-bottom: 20px;
    }

    .choose-form div{
        font-family: 'Helvetica';
        font-size: 25px;
        font-weight: bold;
    }

    .login-form{
        padding-top: 60px;
        margin-left: 15px;
        width: 25%;
    }

    .fill-form{
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }

    .fill{
        height: 40px;
        width: 100%;
        margin-bottom: 20px;
        background-color: #D1DDCD;
        border: 1px solid #009800;
        border-radius: 5px;
    }

    .fill-button{
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 5px;
        height: 40px;
        width: 70%;
        background-color: #009800;
        border-radius: 3px;
        color: white;
        text-decoration: none;
        font-family: 'Helvetica';
        font-size: 18px;
        border: none;
    }

    .register-button{
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 15px;
        height: 25px;
        width: 70%;
        background-color: rgba(255, 255, 255, 0.25);
        border-radius: 3px;
        color: black;
        text-decoration: none;
        font-family: 'Helvetica';
        font-size: 14px;
        border: 1px solid #009800;
    }


    /*FOOTER*/
    .footer-box{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100px;
        margin: auto;
        margin-top: 20px;
        width: 95%;
        border-radius: 10px;
        background-color: #009800;
    }

    .footer-box a{
        color: white;
        font-size: 25px;
        font-family: 'Trebuchet MS';
        text-decoration: none;
        border: none;
    }

    .text-block{
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-direction: row;
        width: 100%;
    }

    .text-block p{
        margin-left: 1px;
        text-align: center;
        font-size: 20px;
        font-family: 'Helvetica';
    }

    .cart-console{
        display: flex;
        justify-content: center;
        width: 100%;
    }

    .cart-console form{
        width: 100%;
        font-family: 'Helvetica';
    }

    .cart-console form button{
            color: white;
            height: 40px;
            width: 100%;
            background-color: #009800;
            border-radius: 5px;
            border: 0;
    }

    .cart-console form a{
                color: white;
                height: 40px;
                width: 95%;
                background-color: #009800;
                border-radius: 5px;
                border: 0;
                font-size: 20px;
                font-family: 'Helvetica';
        }

    .clear-cart{
            display: flex;
            justify-content: center;
            align-items: center;
            border-radius: 3px;
            color: black;
            text-decoration: none;
            font-size: 14px;
            color: white;
            height: 40px;
            width: 95%;
            background-color: #009800;
            border-radius: 5px;
            border: 0;
    }

    .create-order{
        font-size: 20px;
        font-family: 'Helvetica';
    }

    .clear-cart:hover{
      background-color: #17B117;
        }

    .cart-console form button:hover{
            background-color: #17B117;
    }

    .empty-box{
        display: flex;
        justify-content: center;
        height: 400px;
        width: 100%
        text-align: center;
        font-size: 30px;
        font-weight: bold;
        font-family: 'Helvetica';
    }

    .accept-bar{
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 35px;
        margin-bottom: 50px;
    }

    .accept-bar a{
        padding-top: 8px;
        height: 100%;
        width: ${button};
        text-align: center;
        font-size: 18px;
        font-family: 'Helvetica';
        text-decoration: none;
        color: white;
        background-color: #009800;
        border-radius: 5px;
    }

    .accept-bar a:hover{
        background-color: #17B117;
    }

        </style>
</head>
<body>
    <nav class="nav-box">
        <div class="nav-container">
            <form class="search-bar" action="/game-store/store">
                <input class="search" name="game" type="text" placeholder="    Search">
            </form>
            <div class="button-bar"><div class="nav-button">
            <a href="store">Store</a></div><div class="nav-button"><a href="cart">Cart</a></div>
                <div class="nav-button"><a href="order">Orders</a></div>
                            <div class="nav-button"><a href="login">${account}</a></div>
           </div>
        </div>
    </nav>
    <div class="content-box">
        <h1 class="chapter">Orders</h1>
        <div class="cart">
            <%
                            List<Order> list = (List<Order>) request.getAttribute("orders");
                            if(list!=null){
                            for(Order order: list){
                                out.print("<a class='"+((order.getEndDateCourier() == null)?"cart-game":"cart-game-complete")
                                + "' href='order?order=" + order.getId() + "'>");
                                out.print("<img src='"+order.getProducts().get(0).getPicture()+"'>");
                                out.print("<div class='text-block'>");
                                out.print("<h2 class='game-name'>" + order.getId() + "</h2>");
                                out.print("<p>" + order.getStartOrder() + "</p>");
                                out.print("<p>" + order.getPrice() + "$</p>");
                                out.print("</div></a>");

                                if(order.getEndDateCourier() == null && !order.getCancel()){
                                    out.print("<form class='accept-bar'><a href='order?cancel=" + order.getId() + "'>Cancel</a>");
                                }
                                if (SystemUser.getUser().getElementRole() == Role.CLIENT){
                                    out.print("</form>");
                                } else if (SystemUser.getUser().getElementRole() == Role.MANAGER){
                                    out.print("<a href='order?accept=" + order.getId() + "'>Accept</a></form>");
                                } else if (SystemUser.getUser().getElementRole() == Role.STOREKEEPER){
                                    out.print("<a href='order?accept=" + order.getId() + "'>To wrap up</a></form>");
                                } else if (SystemUser.getUser().getElementRole() == Role.COURIER){
                                    out.print("<a href='order?accept=" + order.getId() + "'>Deliver</a></form>");
                                }
                            }
                            }
                        %>
        </div></div>
    <footer class="footer-box">
            <a href="https://www.youtube.com/watch?v=Nj6aM9ljdQU">@Copyright my work</a>
    </footer>
</body>
</html>
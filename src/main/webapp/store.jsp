<%@ page import="services.CriterionService"%>
<%@ page import="models.enums.*"%>
<%@ page import="java.util.List"%>
<%@ page import="services.entity.*"%>
<%@ page import="intarfaces.Entity"%>
<%@ page import="models.*"%>
<%@ page import="services.servlets.StoreServlet"%>
<%@ page import="services.ServiceHibernate"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Store</title>
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

        .game-name{
            margin-top: 10px;
            margin-bottom: 0;
            text-align: center;
        }

        .store-game, .cart-game{
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
            </style>
</head>
<body>
    <nav class="nav-box">
        <div class="nav-container">
            <form class="search-bar" action="/game-store/store">
                <input class="search" name="game" type="text" placeholder="    Search">
            </form>
            <div class="button-bar">
                <div class="nav-button"><a href="store">Store</a></div>
                <div class="nav-button"><a href="cart">Cart</a></div>
                <div class="nav-button"><a href="order">Orders</a></div>
                <div class="nav-button"><a href="login">${account}</a></div>
            </div>
        </div>
    </nav>
    <div class="content-box">
        <h1 class="chapter">Store</h1>
        <div class="store">
        <%
            List<Product> list = (List<Product>) request.getAttribute("products");

            for(Product product: list){
                out.print("<a class='store-game' href='product?game=" + product.getId() + "'>");
                out.print("<img src='https://it.itorrents-igruha.org/uploads/posts/2021-10/1633347917_cover1.jpg' alt='The Guarry'>");
                out.print("<div class='text-block'>");
                out.print("<h2 class='game-name'>" + product.getName() + "</h2>");
                out.print("</div></a>");
            }
        %>
        </div>
    </div>
    <footer class="footer-box">
            <a href="https://www.youtube.com/watch?v=Nj6aM9ljdQU">@Copyright my work</a>
    </footer>
</body>
</html>
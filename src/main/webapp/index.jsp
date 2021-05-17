<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Sales of Cars</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="cssandjs/scripts.js"></script>

    <link rel="stylesheet" href="cssandjs/styles.css">

    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">


</head>
<body onload="fetchAds()">
    <div class="fixed-header" id="fixed-header">
        <h1 class="header">Sales of Cars</h1>
        <div class="search">
            <div class="input-group">
                <div class="form-outline">
                    <input type="search" id="form1" class="form-control" placeholder="Find a car..."/>
                </div>
                <span>
                <button type="button" class="btn btn-outline-light">
                    <i class="fas fa-search"></i>
                </button>
                </span>
            </div>
        </div>
        <c:choose>
            <c:when test="${Seller == null}">
                <button class="btn-grad" onclick="showRegister()">Sign in</button>
                <button class="btn-grad" onclick="showLogin()">Log in</button>
            </c:when>
            <c:otherwise>
                <div id="hello">
                    <h3 >Hello, <c:out value="${Seller.name}"/>!</h3>
                </div>
                <form style="display: inline" method="get">
                    <button formaction="<%=request.getContextPath()%>/createAd.jsp" class="btn-grad">Create a new Ad</button>
                </form>
                <form style="display: inline" method="get">
                    <button formaction="<%=request.getContextPath()%>/logOut.do" class="btn-grad">Log out</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="box" id="box">
    </div>
    <div id="loading" class="container" align="center" style="position: relative; top: 80px">
        <img src="img/ajax-loader.gif" alt="Loading..." />
    </div>

<div class="container">
    <div id="loginBackground">
        <form id="login" action="<%=request.getContextPath()%>/logIn.do" method="post" onsubmit="return validateLogin(this)">
            <div class="card-body">
                    <c:if test="${loginError != null}">
                        <script>document.getElementById('loginBackground').style.display = 'inline-block';</script>
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${loginError}"/>
                        </div>
                    </c:if>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="text" class="form-control" placeholder="Email" name="email" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input type="password" class="form-control" placeholder="Password" name="password" required>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Login" class="btn float-left login_btn">
                    </div>
            </div>
        </form>
    </div>
    <div id="registerBackground">
        <form id="register" action="<%=request.getContextPath()%>/signUp.do" method="post" onsubmit="return validateRegister(this)">
            <div class="card-body">
                <c:if test="${signUpError != null}">
                    <script>document.getElementById('registerBackground').style.display = 'inline-block';</script>
                    <div class="alert alert-danger" role="alert">
                        <c:out value="${signUpError}"/>
                    </div>
                </c:if>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-user"></i></span>
                        </div>
                        <input type="text" class="form-control" placeholder="Name" name="name" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fa fa-envelope"></i></span>
                        </div>
                        <input type="email" class="form-control" placeholder="Email" name="email" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-phone"></i></span>
                        </div>
                        <input type="tel" class="form-control" placeholder="Phone number" name="phone-number" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input id="pwd1" type="password" class="form-control" placeholder="Password" name="password" required>
                    </div>
                    <div class="input-group form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text"><i class="fas fa-key"></i></span>
                        </div>
                        <input id="pwd2" type="password" class="form-control" placeholder="Repeat password" required>
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Register" class="btn float-left login_btn">
                    </div>
            </div>
        </form>
    </div>
    <div id="detailsBackground">
        <div id="details">
        </div>
    </div>
    <p id="userEmail" hidden><c:out value="${Seller.email}"/></p>
</div>
</body>
</html>
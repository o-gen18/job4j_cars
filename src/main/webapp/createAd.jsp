<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Create Ad</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="cssandjs/scripts.js"></script>

    <link rel="stylesheet" href="cssandjs/styles.css">

    <!--Bootsrap 4 CDN-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">

    <!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">

</head>
<body onload="fetchCars(); document.getElementById('yearOfIssue').setAttribute('max', new Date().getFullYear());">
<header>
    <h1 class="header" align="center">Creating a new ad...</h1>
    <form style="display: inline" method="get">
        <button formaction="<%=request.getContextPath()%>/index.jsp" class="btn-grad">Home</button>
    </form>
</header>
<div class="container">
    <form id="createAd" action="<%=request.getContextPath()%>/createAd" method="post" enctype="multipart/form-data">
        <div class="card-body">
            <div class="input-group form-group">
                <label for="file">Upload a photo:   </label>
                <input id="file" class="fileInput" type="file" name="carPhoto" onchange="showPhoto(this)">
                <img id="photo" src="<c:out value="${request.photo}"/>" align="middle" onerror="this.style.display='none'" width="200px" height="200px"/>
            </div>
            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-ad"></i></span>
                </div>
                <input type="text" maxlength="40" class="form-control" placeholder="Name of ad" name="advert" required>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-car-alt"></i></span>
                </div>
                <input class="form-control" id="car" list="cars" name="car" placeholder="Specify the car brand">
                <input type="hidden" id="carHolder" value="<c:out value="${car}"/>">
                <datalist id="cars">
                </datalist>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-car"></i></span>
                </div>
                <input class="form-control" id="carModel" list="carModels" name="carModel" placeholder="Specify the car model">
                <input type="hidden" id="carModelHolder" value="<c:out value="${carModel}"/>">
                <datalist id="carModels">
                </datalist>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                        <svg xmlns="http://www.w3.org/2000/svg" height="16" viewBox="0 0 512 512" width="16"><path d="m512 120v-120h-160v120h60v101.363281c-6.066406 3.511719-11.125 8.570313-14.636719 14.636719h-84.804687c-8.253906-23.277344-30.484375-40-56.558594-40s-48.304688 16.722656-56.558594 40h-84.804687c-3.320313-5.730469-8.011719-10.566406-13.636719-14.042969v-101.957031h59v-120h-160v120h61v100.792969c-12.503906 6.765625-21 19.992187-21 35.207031s8.496094 28.441406 21 35.207031v100.792969h-61v120h160v-120h-59v-101.957031c5.625-3.476563 10.316406-8.3125 13.636719-14.042969h84.804687c8.253906 23.277344 30.484375 40 56.558594 40s48.304688-16.722656 56.558594-40h84.804687c3.511719 6.066406 8.570313 11.125 14.636719 14.636719v101.363281h-60v120h160v-120h-60v-101.363281c11.953125-6.917969 20-19.832031 20-34.636719s-8.046875-27.71875-20-34.636719v-101.363281zm-472-80h80v40h-80zm80 432h-80v-40h80zm352 0h-80v-40h80zm-216-196c-11.027344 0-20-8.972656-20-20s8.972656-20 20-20 20 8.972656 20 20-8.972656 20-20 20zm136-236h80v40h-80zm0 0"/></svg>
                    </span>
                </div>
                <select class="form-control" name="carDrive">
                    <option value="" disabled selected>Select a car drive</option>
                    <option value="FRONT_WHEEL_DRIVE">Front-wheel drive</option>
                    <option value="REAR_WHEEL_DRIVE">Rear-wheel drive</option>
                    <option value="ALL_WHEEL_DRIVE">All-wheel drive</option>
                </select>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                    <svg xmlns="http://www.w3.org/2000/svg"  height="16" viewBox="0 0 512 512" width="16"><path d="m255 .663c-140.931 0-255 114.05-255 255 13.272 338.27 496.768 338.195 510-.002 0-140.926-114.049-254.998-255-254.998zm0 60c97.323 0 178.219 71.668 192.692 165h-385.383c14.472-93.331 95.368-165 192.691-165zm15 255h-30v-30h30zm-200.543 0h43.543c53.485 0 97 43.514 97 97v32.749c-66.369-15.737-119.712-65.487-140.543-129.749zm230.543 129.749v-32.749c0-53.486 43.514-97 97-97h43.543c-20.831 64.262-74.174 114.012-140.543 129.749z"/></svg>
                    </span>
                </div>
                <select class="form-control" name="steeringWheel">
                    <option value="" disabled selected>Select steering wheel position</option>
                    <option value="LEFT_HAND_DRIVE">Left-hand drive</option>
                    <option value="RIGHT_HAND_DRIVE">Right-hand drive</option>
                </select>
            </div>
            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-truck-monster"></i></span>
                </div>
                <select class="form-control" name="bodyType">
                    <option value="" disabled selected>Specify the body type</option>
                    <option value="Sedan">Sedan</option>
                    <option value="Coupe">Coupe</option>
                    <option value="Sports_car">Sports car</option>
                    <option value="Station_wagon">Station wagon</option>
                    <option value="Hatchback">Hatchback</option>
                    <option value="Convertible">Convertible</option>
                    <option value="Minivan">Minivan</option>
                    <option value="Pickup">Pickup</option>
                </select>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-cogs"></i></span>
                </div>
                <select class="form-control" name="transmission">
                    <option value="" disabled selected>Select transmission</option>
                    <option value="Manual">Manual</option>
                    <option value="Automatic">Automatic</option>
                    <option value="CVT">CVT</option>
                    <option value="Semi_automatic">Semi automatic</option>
                </select>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-gas-pump"></i></span>
                </div>
                <select class="form-control" name="fuelType">
                    <option value="" disabled selected>Select the fuel type</option>
                    <option value="Gasoline">Gasoline</option>
                    <option value="Diesel">Diesel</option>
                    <option value="Hybrid">Hybrid</option>
                </select>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-badge-vo-fill" viewBox="0 0 16 16">
  <path d="M12.296 8.394v-.782c0-1.156-.571-1.736-1.362-1.736-.796 0-1.363.58-1.363 1.736v.782c0 1.156.567 1.732 1.363 1.732.79 0 1.362-.576 1.362-1.732z"/>
  <path d="M2 2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2zm11.5 5.62v.77c0 1.691-.962 2.724-2.566 2.724-1.604 0-2.571-1.033-2.571-2.724v-.77c0-1.704.967-2.733 2.57-2.733 1.605 0 2.567 1.037 2.567 2.734zM5.937 11H4.508L2.5 5.001h1.375L5.22 9.708h.057L6.61 5.001h1.318L5.937 11z"/>
</svg>
                    </span>
                </div>
                <input type="number" step=".1" min="0" max="15" class="form-control" placeholder="Engine volume" name="volume" required>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-currency-dollar" viewBox="0 0 16 16">
  <path d="M4 10.781c.148 1.667 1.513 2.85 3.591 3.003V15h1.043v-1.216c2.27-.179 3.678-1.438 3.678-3.3 0-1.59-.947-2.51-2.956-3.028l-.722-.187V3.467c1.122.11 1.879.714 2.07 1.616h1.47c-.166-1.6-1.54-2.748-3.54-2.875V1H7.591v1.233c-1.939.23-3.27 1.472-3.27 3.156 0 1.454.966 2.483 2.661 2.917l.61.162v4.031c-1.149-.17-1.94-.8-2.131-1.718H4zm3.391-3.836c-1.043-.263-1.6-.825-1.6-1.616 0-.944.704-1.641 1.8-1.828v3.495l-.2-.05zm1.591 1.872c1.287.323 1.852.859 1.852 1.769 0 1.097-.826 1.828-2.2 1.939V8.73l.348.086z"/>
</svg>
                    </span>
                </div>
                <input type="number" min="0" class="form-control" placeholder="Price in dollars" name="price" required>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-road"></i></span>
                </div>
                <input type="number" step="5" min="0" max="1500000" class="form-control" placeholder="Mileage in kilometers" name="mileage" required>
                <div class="input-group-append">
                    <span class="input-group-text">Km</span>
                </div>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-calendar2-week-fill" viewBox="0 0 16 16">
  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zm9.954 3H2.545c-.3 0-.545.224-.545.5v1c0 .276.244.5.545.5h10.91c.3 0 .545-.224.545-.5v-1c0-.276-.244-.5-.546-.5zM8.5 7a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zM3 10.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5zm3.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1z"/>
</svg>
                    </span>
                </div>
                <input id="yearOfIssue" type="number" min="1900" class="form-control" placeholder="Year of issue" name="yearOfIssue" required>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-square-text-fill" viewBox="0 0 16 16">
  <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-2.5a1 1 0 0 0-.8.4l-1.9 2.533a1 1 0 0 1-1.6 0L5.3 12.4a1 1 0 0 0-.8-.4H2a2 2 0 0 1-2-2V2zm3.5 1a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9zm0 2.5a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9zm0 2.5a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5z"/>
</svg>
                    </span>
                </div>
                <textarea id="description" class="form-control" placeholder="Type description..." name="description"></textarea>
            </div>

            <div class="input-group form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"><i class="fas fa-map-marked-alt"></i></span>
                </div>
                <input type="text" class="form-control" placeholder="Address" name="address" required>
            </div>

            <div class="form-group text-center">
                <button type="submit" class="btn btn-outline-primary">Submit</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>

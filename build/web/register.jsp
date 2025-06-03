<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Register Here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
    </style>
  </head>
  <body>
    <jsp:include page="header.jsp"/>
    <%
         String message=request.getParameter("message");
    %>
    <div class="container mt-5 mb-5">
      <div class="row justify-content-center">
      <form action="RegisterServlet" class="col-md-6 myform p-3 m-3" method="post">
        <div class="text-center m-3">
          <h2 class="text-primary">Registration Form</h2>
         <%if(message!=null){%>
          <p class="text-primary"><%=message%></p>
          <%}%>
        </div>
        <div class="row">
          <div class="col-md-6 form-group mt-1">
            <label for="user_name">Name</label>
            <input
              type="text"
              class="form-control"
              id="user_name"
              name="username"
              required
            />
          </div>
          <div class="col-md-6 form-group mt-1">
            <label for="user_email">Email</label>
            <input
              type="email"
              class="form-control"
              name="useremail"
              id="user_email"
              required
            />
          </div>
        </div>

        <div class="form-group">
        <label for="user_address">Address</label>
        <textarea
            name="useraddress"
            id="user_address"
            rows="2"
            class="form-control mt-1"
          ></textarea>
        </div>

          <div class="row">
            <div class="col-md-6 form-group mt-1">
              <label for="mobile_no">Mobile</label>
              <input
                type="number"
                class="form-control"
                id="mobile_no"
                name="mobileno"
                required
              />
            </div>
            <div class="col-md-6 form-group mt-1">
              <label for="pincode">Pin Code</label>
              <input
                type="number"
                class="form-control"
                name="pincode"
                id="pincode"
                required
              />
            </div>
          </div>

          <div class="row">
            <div class="col-md-6 form-group mt-1">
              <label for="password">Password</label>
              <input
                type="password"
                class="form-control"
                id="password"
                name="password"
                required
              />
            </div>
            <div class="col-md-6 form-group mt-1">
              <label for="confpassword">Confirm Password</label>
              <input
                type="password"
                class="form-control"
                name="confpassword"
                id="confpassword"
                required
              />
            </div>
          </div>

          <div class="row text-center mt-3 mb-3">
            <div class="col-md-6 form-group">
             <button type="reset" class="btn btn-danger mt-2">Reset</button>
            </div>
            <div class="col-md-6 form-group">
              <button type="submit" class="btn btn-primary mt-2">Register</button>
            </div>
          </div>
      </form>
      </div>
  </div>
    <jsp:include page="footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
  </body>
</html>
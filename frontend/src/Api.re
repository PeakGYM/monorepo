open Belt;

let getHeaders =
  Fetch.HeadersInit.make({
    "Content-Type": "application/json",
    "Accept": "application/json",
  });

let fetch = (path: string, params) =>
  Fetch.fetchWithInit(path, params)
  |> Repromise.Rejectable.fromJsPromise
  |> Repromise.Rejectable.andThen(res => {
       open Result;
       let status = Fetch.Response.status(res);
       switch (status) {
       | 200
       | 201 =>
         Fetch.Response.json(res)
         |> Repromise.Rejectable.fromJsPromise
         |> Repromise.Rejectable.map(json => Ok(json))

       | status =>
         switch (status) {
         | 401
         | 403
         | _ => ()
         };

         Fetch.Response.text(res)
         |> Repromise.Rejectable.fromJsPromise
         |> Repromise.Rejectable.map(text => Error((status, text)));
       };
     });

let createBody = body => body |> Json.stringify |> Fetch.BodyInit.make;

let get = path =>
  fetch(path, Fetch.RequestInit.make(~method_=Get, ~headers=getHeaders, ()));

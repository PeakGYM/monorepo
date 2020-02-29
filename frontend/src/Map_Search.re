type places = array(place)
and place = {
  id: string,
  displayName: string,
  lat: string,
  lon: string,
  bounds: array(string),
};

let decode = json => {
  Json.Decode.(
    json
    |> array(json =>
         {
           id: json |> field("place_id", int) |> string_of_int,
           displayName:
             json
             |> field("display_name", string)
             |> Js.String.split(",")
             |> Array.reverse
             |> Array.map(_, Js.String.trim)
             |> Js.Array.joinWith(", "),
           lat: json |> field("lat", string),
           lon: json |> field("lon", string),
           bounds: json |> field("boundingbox", array(string)),
         }
       )
  );
};

let getPlaces = (~value) => {
  let acceptLanguage = "pl-PL";

  Api.get(
    {j|https://nominatim.openstreetmap.org/search?format=json&q=$value&limit=5&addressdetails=1&accept-language=$acceptLanguage|j},
  )
  |> Repromise.Rejectable.map(Result.map(_, decode));
};

[@react.component]
let make = () => {

  <div />;
};

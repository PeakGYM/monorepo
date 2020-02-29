module Gym = [%graphql
  {|
  query Gyms(
    $lat: Float!,
    $lng: Float!
    ) {
    gyms(
      lat: $lat,
      lng: $lng
    ) {
     id
     name
     location {
       lat
       lng
     }
    }
  }
|}
];

let use = (~lat, ~lng) => {
  let variables =
    Gym.makeVariables(
      ~lat=lat,
      ~lng=lng,
      (),
    );

  ApolloHooks.useQuery(~variables, Gym.definition);
};

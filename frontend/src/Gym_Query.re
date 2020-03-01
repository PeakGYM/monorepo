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
     coachIds
     coaches {
       id
       name
       pictureUrl
     }
    }
  }
|}
];

let use = (~lat, ~lng) => {
  let variables = Gym.makeVariables(~lat, ~lng, ());

  ApolloHooks.useQuery(~variables, Gym.definition);
};

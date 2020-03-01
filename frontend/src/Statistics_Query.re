module Measurements = [%graphql
  {|
  query Measurements(
    $clientId: String!
    ) {
    getAllFor(
      clientId: $clientId,
    ) {
       id
       clientId
       timestamp
       weight
       neck
       leftBicep
       rightBicep
       leftForearm
       rightForearm
       chest
       waist
       hip
       rightThigh
       leftThigh
       rightCalf
       leftCalf
    }
  }
|}
];

let use = (~clientId) => {
  let variables = Measurements.makeVariables(~clientId, ());

  ApolloHooks.useQuery(~variables, Measurements.definition);
};

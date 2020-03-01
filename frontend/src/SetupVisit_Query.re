module Coaches = [%graphql
  {|
  query Coaches(
$ids: [String!]!
    ) {
    getCoachesByIds(
        ids: $ids
    ) {
        id
        name
        pictureUrl
    }
  }
|}
];

let use = (~coachId) => {
  let variables = Coaches.makeVariables(~ids=[|coachId|], ());

  ApolloHooks.useQuery(
    ~fetchPolicy=NetworkOnly,
    ~variables,
    Coaches.definition,
  );
} /* module Query = ApolloHooks.Query.Make(Training)*/;
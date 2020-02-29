let uri = "https://api-wwh.codevillains.me/graphql";

/* Create an InMemoryCache */
let inMemoryCache = ApolloInMemoryCache.createInMemoryCache();

/* Create an HTTP Link */
let httpLink = ApolloLinks.createHttpLink(~uri, ());

let client =
  ReasonApollo.createApolloClient(~link=httpLink, ~cache=inMemoryCache, ());

[@react.component]
let make = () => {
  let route = Router.useRoute();

  <ReasonApollo.Provider client>
    <ApolloHooks.Provider client>
      <div>
        <Navbar />
        {switch (route) {
         | Main => <Main_View />
         | Schedule => <Schedule_View />
         | Day(id) => <Day_View id />
         | Map => <Map_View />
         }}
      </div>
    </ApolloHooks.Provider>
  </ReasonApollo.Provider>;
};
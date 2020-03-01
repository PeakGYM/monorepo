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
      <div
        className=TW.([Position(Relative), MinHeight(MinHScreen)] |> make)>
        <div style={ReactDOMRe.Style.make(~paddingBottom="120px", ())}>
          {switch (route) {
           | Main => <Main_View />
           | Schedule => <Schedule_View />
           | DayEdit(id) => <Day_Edit_View id />
           | DayLog(id) => <Day_Log_View id />
           | Pick(id) => <Pick_View id />
           | Map => <Map_View />
           }}
        </div>
        <Navbar />
      </div>
    </ApolloHooks.Provider>
  </ReasonApollo.Provider>;
};
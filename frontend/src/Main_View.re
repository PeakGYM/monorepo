[@react.component]
let make = () =>
  <div className=TW.([Padding(P8)] |> make)>
    <button onClick={_ => Router.go(Router.Schedule)}>
      <Text content="Click" />
    </button>
  </div>;
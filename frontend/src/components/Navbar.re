[@react.component]
let make = () =>
  <nav className=TW.([Display(Flex), Padding(P8)] |> make)>
    <span className=TW.([TextColor(TextWhite)] |> make)>
      <Text content="Peak GYM" />
    </span>
  </nav>;
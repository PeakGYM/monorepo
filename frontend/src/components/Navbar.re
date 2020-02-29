[@react.component]
let make = () =>
  <nav
    className=TW.(
      [Display(Flex), Padding(P8), BoxShadow(ShadowLg)] |> make
    )>
    <span className=TW.([TextColor(TextWhite)] |> make)>
      <Text content="Peak GYM" />
    </span>
  </nav>;
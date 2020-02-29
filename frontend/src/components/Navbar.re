[@react.component]
let make = () =>
  <nav
    className=TW.(
      [Display(Flex), Padding(P8), BackgroundColor(BgGreen400)] |> make
    )>
    <span className=TW.([TextColor(TextWhite)] |> make)>
      "Peak GYM"->React.string
    </span>
  </nav>;
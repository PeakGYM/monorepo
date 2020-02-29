[@react.component]
let make = () =>
  <nav
    className=TW.(
      [Display(Flex), Padding(P8), BackgroundColor(BgGreen400)] |> make
    )>
    <h1 className=TW.([FontSize(Text5xl), TextColor(TextWhite)] |> make)>
      "Peak GYM"->React.string
    </h1>
  </nav>;
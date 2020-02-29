package com.guys.coding.hackathon.backend.api.graphql.schema.user

import com.guys.coding.hackathon.backend.Services
import com.guys.coding.hackathon.backend.api.graphql.schema.MutationHolder
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserInputTypes._
import com.guys.coding.hackathon.backend.api.graphql.schema.user.UserOutputTypes._
import com.guys.coding.hackathon.backend.api.graphql.service.GraphqlSecureContext
import sangria.schema.{fields, Argument, Field, FloatType, LongType, OptionInputType, StringType}

class MeasurementMutation(services: Services) extends MutationHolder {
  val ClientIdArg     = Argument("clientId", StringType)
  val TimestampArg    = Argument("timestamp", LongType)
  val WeightArg       = Argument("weight", OptionInputType(FloatType))
  val NeckArg         = Argument("neck", OptionInputType(FloatType))
  val LeftBicepArg    = Argument("leftBicep", OptionInputType(FloatType))
  val RightBicepArg   = Argument("rightBicep", OptionInputType(FloatType))
  val LeftForearmArg  = Argument("leftForearm", OptionInputType(FloatType))
  val RightForearmArg = Argument("rightForearm", OptionInputType(FloatType))
  val ChestArg        = Argument("chest", OptionInputType(FloatType))
  val WaistArg        = Argument("waist", OptionInputType(FloatType))
  val HipArg          = Argument("hip", OptionInputType(FloatType))
  val RightThighArg   = Argument("rightThigh", OptionInputType(FloatType))
  val LeftThighArg    = Argument("leftThigh", OptionInputType(FloatType))
  val RightCalfArg    = Argument("rightCalf", OptionInputType(FloatType))
  val LeftcalfArg     = Argument("leftCalf", OptionInputType(FloatType))

  override def mutationFields(): List[Field[GraphqlSecureContext, Unit]] =
    fields[GraphqlSecureContext, Unit](
      Field(
        "addMeasurement",
        MeasurementType,
        arguments = List(
          ClientIdArg,
          TimestampArg,
          WeightArg,
          NeckArg,
          LeftBicepArg,
          RightBicepArg,
          LeftForearmArg,
          RightForearmArg,
          ChestArg,
          WaistArg,
          HipArg,
          RightThighArg,
          LeftThighArg,
          RightCalfArg,
          LeftcalfArg
        ),
        resolve = c =>
          c.ctx.authorizedF { _ =>
            services.measurementsRepository
              .insert(
                MeasurementGraphql(
                  c.arg(ClientIdArg),
                  c.arg(TimestampArg),
                  c.arg(WeightArg),
                  c.arg(NeckArg),
                  c.arg(LeftBicepArg),
                  c.arg(RightBicepArg),
                  c.arg(LeftForearmArg),
                  c.arg(RightForearmArg),
                  c.arg(ChestArg),
                  c.arg(WaistArg),
                  c.arg(HipArg),
                  c.arg(RightThighArg),
                  c.arg(LeftThighArg),
                  c.arg(RightCalfArg),
                  c.arg(LeftcalfArg)
                ).toDomain
              )
              .unsafeToFuture()
          }
      )
    )
}
